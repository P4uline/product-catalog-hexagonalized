package fr.catalog.infra.file;

import fr.catalog.business.PortFileGateway;

import java.io.File;

public class FilesystemGatewayAdapter implements PortFileGateway {
    
    @Override
    public File getFileByName(String filename) {
        return new File(filename);
    }
}
