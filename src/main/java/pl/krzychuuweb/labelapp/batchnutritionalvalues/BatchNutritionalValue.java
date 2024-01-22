package pl.krzychuuweb.labelapp.batchnutritionalvalues;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;

@Entity
@Table(name = "batches_nutritional_values")
public class BatchNutritionalValue {

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "nutritional_value_id")
    private NutritionalValue nutritionalValue;

    @ManyToOne
    @JoinColumn(name = "sub_nutritional_value_id")
    private SubNutritionalValue subNutritionalValue;

    private String value;

    BatchNutritionalValue() {
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(final Batch batch) {
        this.batch = batch;
    }

    public NutritionalValue getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(final NutritionalValue nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public SubNutritionalValue getSubNutritionalValue() {
        return subNutritionalValue;
    }

    public void setSubNutritionalValue(final SubNutritionalValue subNutritionalValue) {
        this.subNutritionalValue = subNutritionalValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
