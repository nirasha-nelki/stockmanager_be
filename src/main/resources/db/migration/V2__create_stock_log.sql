CREATE TABLE stock_log (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           product_id INT NOT NULL,
                           quantity_change INT NOT NULL,
                           timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_stock_product
                               FOREIGN KEY (product_id) REFERENCES product(product_id)
);
