package modzo.compare.command.core.domain.item.commands.create.photo;

public interface PhotoUploader {
    void upload(Photo photo);

    class Photo {
        private final String name;
        private final String url;

        public Photo(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
