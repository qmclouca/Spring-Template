package com.qmclouca.base.utils.JwtGenerator;
import com.qmclouca.base.models.Client;

import java.util.Map;
public interface JwtGeneratorInterface {
    Map<String, String> generateToken(Client client);
}
