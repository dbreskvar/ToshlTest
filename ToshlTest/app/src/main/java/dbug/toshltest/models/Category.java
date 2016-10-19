package dbug.toshltest.models;

public class Category {

    /**
     {
     "id": "52688524",
     "name": "Expense",
     "name_override": true,
     "modified": "2016-10-19 15:24:52.707",
     "type": "expense",
     "deleted": false,
     "transient_created": "2016-10-19 15:24:52.779",
     "counts": {
     "entries": 0,
     "tags_used_with_category": 0,
     "tags": 0,
     "budgets": 0
     }
     }
     */

    String id, name, name_override, modified, type, transient_created, extra;
    Boolean deleted;
    Counts counts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_override() {
        return name_override;
    }

    public void setName_override(String name_override) {
        this.name_override = name_override;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getTransient_created() {
        return transient_created;
    }

    public void setTransient_created(String transient_created) {
        this.transient_created = transient_created;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Counts getCounts() {
        return counts;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }
}
