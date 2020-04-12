package octava.converter.populator;

import octava.converter.Populator;
import octava.model.media.MediaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.StringJoiner;

import static org.apache.commons.lang3.BooleanUtils.negate;

public class MultipartFile2MediaPopulator<S extends MultipartFile, T extends MediaModel> implements Populator<S, T> {

    @Autowired
    private Environment environment;

    @Override
    public void populate(final S source, final T target) {

        final StringJoiner fullPath = new StringJoiner("\\");

        final String userPath = "admin";

        fullPath.add(environment.getProperty("storage.local.folder")).add(userPath);

        final String path = fullPath.toString();
        final File folder = new File(path);

        if (negate(folder.exists())) {
            folder.mkdir();
        }

        final String fileName = fullPath.add(source.getOriginalFilename()).toString();
        target.setPath(folder.getPath());
        target.setFileName(fileName);
    }
}
