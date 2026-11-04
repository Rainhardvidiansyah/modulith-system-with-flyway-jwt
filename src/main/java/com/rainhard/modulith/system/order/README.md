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
````
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
 }
````

````
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(updatable = false, nullable = false, name = "order_id")
    private UUID orderId;

    @Column(updatable = false, nullable = false, name = "product_id")
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 12, scale = 2, updatable = false)
    private BigDecimal price;
}
````

### Relationship between order and order_item in real scenario:
#### **Example**
````
==============================
INVOICE
==============================
Order ID  : 019f...           ← orders.id
User      : Rainhard           ← orders.user_id
Status    : PENDING            ← orders.status
Date      : 04 July 2026       ← orders.created_at
------------------------------
ITEM:
Laptop ThinkPad  x2  @15jt = 30jt    ← order_items
Mouse MX3        x3  @1.3jt = 3.9jt  ← order_items
------------------------------
TOTAL     : Rp 33.900.000     ← orders.total_amount
==============================
````
### Events
* **Order placed**:
````
inventory — needs to know:  
productId → which product’s stock is being reserved
quantity → how many are being reserved

payment — needs to know:
orderId → which order is being paid for
totalAmount → how much is due

notification — needs to know:
orderId → which order
userId → who to send the notification to
````

* **Order placed _event_ will be used in**:

- Inventory — reserveStock ✅
- OrderPlaced → inventory listen → reserveStock(productId, quantity)
- Customer checkout → stock reserved for a while

* **Table**:

| Event            | From module | Method inventory |
|------------------|-------------|------------------|
| OrderPlaced      | order       | reserveStock     |
| OrderCancelled   | order       | releaseStock     |
| PaymentConfirmed | payment     | deductStock      |

### Methods


### Request and Response
- Request in client:
````
[
  {
    "productId": "239dae3a-5c35-49da-ba55-a7713f5fb027",
    "quantity": 2
  },
  {
    "productId": "5f33104f-610f-4c1b-8402-b737c5106fe4",
    "quantity": 1
  }
]
````

Response:
````
{
	"metadata": {
		"message": "OK",
		"path": "/api/v1/order",
		"requestId": "d3ff4291-4f54-4b66-aa65-ba899b429401",
		"status": 201,
		"timestamp": "2026-07-05T02:59:17.097796Z"
	},
	"data": {
		"orderId": "019f3037-47bc-75fb-b7d4-985a11af5c2e",
		"userId": "239dae3a-5c35-49da-ba55-a7713f5fb027",
		"status": "PENDING",
		"totalAmount": 37950000.00,
		"items": [
			{
				"productId": "239dae3a-5c35-49da-ba55-a7713f5fb027",
				"quantity": 2,
				"unitPrice": 18500000.00
			},
			{
				"productId": "5f33104f-610f-4c1b-8402-b737c5106fe4",
				"quantity": 1,
				"unitPrice": 950000.00
			}
		],
		"createdAt": "2026-07-05T02:59:17.046956Z"
	}
}
````