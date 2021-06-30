/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package fr.insee.sugoi.jms.model;

import java.io.Serializable;

import org.apache.activemq.command.ActiveMQMessage;

import fr.insee.sugoi.core.model.ProviderResponse;

public class BrokerResponse extends ActiveMQMessage implements Serializable {

  private String comment;

  private ProviderResponse providerResponse;

  public BrokerResponse() {
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public ProviderResponse getProviderResponse() {
    return providerResponse;
  }

  public void setProviderResponse(ProviderResponse providerResponse) {
    this.providerResponse = providerResponse;
  }

}
