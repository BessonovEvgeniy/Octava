package octava.model;

public class NullStoredFileModel extends StoredFileModel {

    @Override
    public boolean isNull() {
        return true;
    }
}