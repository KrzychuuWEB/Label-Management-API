package pl.krzychuuweb.labelapp.batchnutritionalvalues;

import jakarta.persistence.*;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;

@Entity
@Table(name = "batches_nutritional_values")
public class BatchNutritionalValue {

    @EmbeddedId
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @MapsId("nutritional_value_id")
    @ManyToOne
    @JoinColumn(name = "nutritional_value_id")
    private NutritionalValue nutritionalValue;

    @MapsId("sub_nutritional_value_id")
    @ManyToOne
    @JoinColumn(name = "sub_nutritional_value_id")
    private SubNutritionalValue subNutritionalValue;

    private String value;

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
