package octava.model.media;

import lombok.Data;
import octava.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MEDIA")
public @Data class MediaModel extends BaseModel {

    protected String path;

    protected String url;

    protected String fileName;

    public boolean isNull() {
        return false;
    }

    public String getFullName() {
        return path + "\\" + fileName;
    }
}
