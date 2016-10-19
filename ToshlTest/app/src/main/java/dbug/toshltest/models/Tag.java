package dbug.toshltest.models;

public class Tag {

    String id, name, modified, type, category, transient_created;
    Integer count;
    Boolean name_override, deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTransient_created() {
        return transient_created;
    }

    public void setTransient_created(String transient_created) {
        this.transient_created = transient_created;
    }

    public Boolean getName_override() {
        return name_override;
    }

    public void setName_override(Boolean name_override) {
        this.name_override = name_override;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
