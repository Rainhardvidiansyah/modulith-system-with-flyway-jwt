CREATE TABLE inventory_batch (
    id UUID PRIMARY KEY,
    inventory_id UUID NOT NULL,
    batch_number VARCHAR(50) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    received_date TIMESTAMP NOT NULL DEFAULT now(),
    cost_price NUMERIC(12,2) NOT NULL,

    CONSTRAINT uq_inventory_batch UNIQUE (inventory_id, batch_number)
);

CREATE INDEX idx_inventory_batch_inventory_id ON inventory_batch (inventory_id);
CREATE INDEX idx_inventory_batch_received_date ON inventory_batch (received_date);