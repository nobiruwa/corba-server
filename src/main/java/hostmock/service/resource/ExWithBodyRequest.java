package hostmock.service.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="exwithbodyrequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExWithBodyRequest {
    public String exname;
    public String body;

    public ExWithBodyRequest() {
    }
    public ExWithBodyRequest(String exname, String body) {
        this.exname = exname;
        this.body = body;
    }
}
