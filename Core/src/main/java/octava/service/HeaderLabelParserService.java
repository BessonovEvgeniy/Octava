package octava.service;


import octava.model.observation.header.HeaderLabel;

public interface HeaderLabelParserService<T extends HeaderLabel> {

    T parse(String line);
}
