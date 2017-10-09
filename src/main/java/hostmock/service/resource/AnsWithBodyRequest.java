package hostmock.service.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="answithbodyrequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnsWithBodyRequest {
    public String body;

    public AnsWithBodyRequest() {
    }
    public AnsWithBodyRequest(String body) {
        this.body = body;
    }
}
