package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import jakarta.persistence.*;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;

@Entity
@Table(name = "batches_nutritional_values")
public class BatchNutritionalMapping {

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


    public static final class BatchNutritionalMappingBuilder {
        private Batch batch;
        private NutritionalValue nutritionalValue;
        private SubNutritionalValue subNutritionalValue;
        private String value;

        private BatchNutritionalMappingBuilder() {
        }

        public static BatchNutritionalMappingBuilder aBatchNutritionalMapping() {
            return new BatchNutritionalMappingBuilder();
        }

        public BatchNutritionalMappingBuilder withBatch(Batch batch) {
            this.batch = batch;
            return this;
        }

        public BatchNutritionalMappingBuilder withNutritionalValue(NutritionalValue nutritionalValue) {
            this.nutritionalValue = nutritionalValue;
            return this;
        }

        public BatchNutritionalMappingBuilder withSubNutritionalValue(SubNutritionalValue subNutritionalValue) {
            this.subNutritionalValue = subNutritionalValue;
            return this;
        }

        public BatchNutritionalMappingBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public BatchNutritionalMapping build() {
            BatchNutritionalMapping batchNutritionalMapping = new BatchNutritionalMapping();
            batchNutritionalMapping.setBatch(batch);
            batchNutritionalMapping.setNutritionalValue(nutritionalValue);
            batchNutritionalMapping.setSubNutritionalValue(subNutritionalValue);
            batchNutritionalMapping.setValue(value);
            return batchNutritionalMapping;
        }
    }
}
