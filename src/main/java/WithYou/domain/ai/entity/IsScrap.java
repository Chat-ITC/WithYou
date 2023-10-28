package WithYou.domain.ai.entity;

public enum IsScrap {
    No("NO"),
    YES("YES");
    private String value;

    IsScrap(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
