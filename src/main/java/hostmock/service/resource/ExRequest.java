package hostmock.service.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="exrequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExRequest {
    public String exname;

    public ExRequest() {
    }
    public ExRequest(String exname) {
        this.exname = exname;
    }
}
