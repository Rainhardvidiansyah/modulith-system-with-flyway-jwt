# Module Order

### Explanation



### Folder Structure

```
order
├── OrderCanceled.java
├── OrderFacade.java
├── OrderPlaced.java
├── README.md
├── controller
│   └── OrderController.java
├── dto
│   ├── OrderItemRequest.java
│   ├── OrderRequest.java
│   └── OrderResponse.java
└── internal
    ├── JdbcOrderRepository.java
    ├── JpaOrderRepository.java
    ├── Order.java
    ├── OrderItems.java
    └── OrderService.java
```

### Migration File
- **table order**:

````
 CREATE TABLE orders (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  total_amount NUMERIC(12,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now()
  );
````

- **Table order item**:

```
CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(12,2) NOT NULL
);
````

### Entity
### Events
### Methods
