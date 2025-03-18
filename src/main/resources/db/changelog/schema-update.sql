-- Set 'id' column to NOT NULL
ALTER TABLE customer
ALTER COLUMN id SET NOT NULL;

ALTER TABLE "order"
ALTER COLUMN id SET NOT NULL;

ALTER TABLE product
ALTER COLUMN id SET NOT NULL;



-- Synchronize the sequence with the current maximum value of 'id'
SELECT setval('customer_id_seq', (SELECT MAX(id) FROM customer));

SELECT setval('order_id_seq', (SELECT MAX(id) FROM "order"));

SELECT setval('product_id_seq', (SELECT MAX(id) FROM product));



-- Link the 'id' column to the sequence for auto-incrementing
ALTER TABLE customer
ALTER COLUMN id SET DEFAULT nextval('customer_id_seq');

ALTER TABLE "order"
ALTER COLUMN id SET DEFAULT nextval('order_id_seq');

ALTER TABLE product
ALTER COLUMN id SET DEFAULT nextval('product_id_seq');
