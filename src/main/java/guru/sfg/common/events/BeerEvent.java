package guru.sfg.common.events;

import com.harish.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -948788686369205607L;

    private BeerDto beerDto;
}
