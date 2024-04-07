-- -----------------------------------------------------
-- 1.Task
-- Get numbers of vehicles sold (loans disbursed) in Jan and Feb 2020 per each vehicle make.
-- a) Display only those makes where total sales are more than 1000 units
SELECT vma.id      AS vehicle_id,
       vma.name    AS vehicle_name,
       COUNT(l.id) AS vehicle_sold
FROM m_loan AS l
         JOIN `asset-schema`.asset AS a ON l.id = a.m_loan_id
         JOIN `asset-schema`.vehicle_model vmo ON a.model_id = vmo.id
         JOIN `asset-schema`.vehicle_make vma ON vmo.vehicle_make_id = vma.id
WHERE l.disbursedon_date >= '2020-01-01'
  AND l.disbursedon_date < '2020-03-01'
GROUP BY vma.id
HAVING COUNT(l.id) > 1000;
-- -----------------------------------------------------
-- b) Display full sales data including all makes from database (including those where sales are not made)
SELECT vma.id      AS vehicle_id,
       vma.name    AS vehicle_name,
       COUNT(l.id) AS vehicle_sold
FROM m_loan AS l
         JOIN `asset-schema`.asset AS a ON l.id = a.m_loan_id AND l.disbursedon_date >= '2020-01-01' AND l.disbursedon_date < '2020-03-01'
         JOIN `asset-schema`.vehicle_model vmo ON a.model_id = vmo.id
         RIGHT JOIN `asset-schema`.vehicle_make vma ON vmo.vehicle_make_id = vma.id
GROUP BY vma.id;

-- -----------------------------------------------------
-- 2. Task
-- Get current weekly payment amount for each loan. Table m_loan_repayment_schedule contains weekly payment records.
-- Weekly payment record should be selected for the first week where obligations are not met (value for field completed_derived=0).
-- Use principal_amount plus interest_amount to acquire weekly payment amount.
SELECT s1.loan_id,
       s1.duedate,
       COALESCE(s1.principal_amount, 0) + COALESCE(s1.interest_amount, 0) AS amount
FROM m_loan_repayment_schedule s1
         LEFT JOIN m_loan_repayment_schedule s2
                   ON s1.loan_id = s2.loan_id
                       AND s2.completed_derived = 0
                       AND s2.duedate < s1.duedate
WHERE s1.completed_derived = 0
  AND s2.loan_id IS NULL
ORDER BY s1.loan_id;

-- -----------------------------------------------------
-- 3. Task
-- Calculate current balance (scheduled amount - payed amount) for each loan.
-- Use tables m_loan_repayment_schedule for payment schedule data.
-- Total scheduled payment amount on current date must be calculated by sum of all amount field values.
-- Some values can be null. Payment data are stored in table m_loan_transaction.
SELECT loan_id,
       SUM(amount)
FROM (SELECT loan_id,
             COALESCE(principal_amount, 0) +
             COALESCE(interest_amount, 0) +
             COALESCE(fee_charges_amount, 0) +
             COALESCE(penalty_charges_amount, 0) AS amount
      FROM m_loan_repayment_schedule
      UNION ALL
      SELECT loan_id,
             amount * IF(is_reversed, 1, -1) AS amount
      FROM m_loan_transaction) t
GROUP BY loan_id;