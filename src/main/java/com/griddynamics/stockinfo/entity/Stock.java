package com.griddynamics.stockinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("stock")
public class Stock implements Persistable<Long>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("symbol")
    private String symbol;

    @Column("latest_price")
    private BigDecimal latestPrice;

    @Column("change")
    private BigDecimal change;

    @Column("previous_volume")
    private Integer previousVolume;

    @Column("volume")
    private Integer volume;

    @Column("company_name")
    private String companyName;

    public Long getId() {
        return id;
    }

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!Objects.equals(id, stock.id)) return false;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }
}
