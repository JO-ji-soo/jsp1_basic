package project.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ProductVO {
    private String pcode;
    private String category;
    private String pname;
    private int price;

    @Override
    public String toString() {
        return String.format("6%s 16%s 30%s\t 8%d", pcode,category,pname,price);
    }
}