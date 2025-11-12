package org.homemade.product.service.query;

import lombok.Value;

@Value
public class FindProductQuery {
    String name;
    String brand;
}
