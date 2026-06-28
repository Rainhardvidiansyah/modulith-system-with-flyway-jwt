# Module Document: Inventory

### Folder Structure:
````
inventory/
├── package-info.java
├── InventoryFacade.java
├── controller/
│   └── InventoryController.java
├── dto/
│   ├── InventoryRequest.java
│   └── InventoryResponse.java
└── internal/
    ├── Inventory.java
    ├── InventoryRepository.java
    ├── InventoryService.java
    └── InventoryListener.java
````
## Responsibility
Manages stock levels per product per warehouse.
Tracks available and reserved quantities.

| Characteristics                          | Anemic Domain Model | Rich Domain Model |
|:-----------------------------------------|:--------------------|:------------------|
| **Business Logic Location**              | Located outside the entity (within the *Service* or *Controller* class) | Located directly within the domain entity object |
| **Entity Condition**                     | Contains only data properties with public *getters* and *setters* | Contains data properties and operational methods (*behavior*) |
| **Encapsulation**                        | Weak; entity state can be freely modified from outside | Strong; state can only be modified through entity methods |
| **Validation Rules (Invariants)**        | Handled by *Service*; risk of invalid data if *Service* is bypassed | Enforced by the entity; object cannot exist in an invalid state |
| **Code Scalability**                     | Hard to maintain in large systems due to logic accumulation in *Service* | Easier to maintain because logic is well distributed across domains |
| **Object Nature**                        | Passive (just a data structure / *Data Transfer Object*) | Active (represents real business concepts) |
| **Project Suitability**                  | Simple applications, CRUD-based, or small-scale projects | Complex applications with rich business rules (DDD) |


## Methods
``This system uses 3 core actions (reserve, release, deduct) to manage two stock indicators (quantity_available, reserved_quantity).``

| Term                     | Action / Scenario                                                                 | Impact                                                                                      |
|:-------------------------|:----------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **`reserve`**            | *"Sir, please set aside 2 packs of Indomie for me, I'll pick them up later"*     | Moves stock from `quantity_available` to `reserved_quantity`.                               |
| **`release`**            | *"Actually, cancel it, I’m not buying anymore"*                                  | Returns stock from `reserved_quantity` back to `quantity_available` (Indomie goes back to the shelf). |
| **`deduct`**             | Indomie is actually taken home and paid for.                                     | Permanently reduces `reserved_quantity` from the total inventory.                           |
| **`quantity_available`** | Physical goods that are ready for sale.                                          | Number of Indomie packs on the shelf that are freely available for other customers.         |
| **`reserved_quantity`**  | Goods that are currently reserved.                                               | Number of Indomie packs that have been “set aside” under the cashier’s desk.                |

---

## Events
#### Consumed
| Event | Source Module | Action |
|-------|--------------|--------|
| `ProductCreated` | `product` | Initialize stock entry |

### Published
| Event | Trigger |
|-------|---------|
| `StockDepleted` | When `quantity_available` reaches 0 |
| `StockReserved` | When order reserves stock |

## Database
- Table: `inventory`
- No FK constraints — referential integrity via domain events
- Migration location: `db/migration/inventory/`

## Dependencies
- Depends on: `product` (event only, no direct import)

## Implementing FIFO (First in First Out) in Inventory Module
- Table Migration:
````
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

````
- Method to reduce the batch:
````
   // business method — FIFO: reduce the quantity for this batch
    public void deduct(int amount) {
        if (amount > this.quantity) {
            throw new IllegalArgumentException("Insufficient batch quantity");
        }
        this.quantity -= amount;
    }
  ````
  