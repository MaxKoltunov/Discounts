CREATE TABLE IF NOT EXISTS discountSchema.discounts (
    id BIGINT PRIMARY KEY DEFAULT nextval('discountSchema.discounts_id_seq'),
    type VARCHAR(255) NOT NULL,
    product_type VARCHAR(255) NOT NULL
);