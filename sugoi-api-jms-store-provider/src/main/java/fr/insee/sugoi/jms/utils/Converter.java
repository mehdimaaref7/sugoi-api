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
package fr.insee.sugoi.jms.utils;

import fr.insee.sugoi.core.model.ProviderRequest;
import fr.insee.sugoi.core.model.ProviderResponse;
import fr.insee.sugoi.core.model.ProviderResponse.ProviderResponseStatus;
import fr.insee.sugoi.core.model.SugoiUser;
import fr.insee.sugoi.model.Application;
import fr.insee.sugoi.model.Group;
import fr.insee.sugoi.model.Organization;
import fr.insee.sugoi.model.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class Converter {

  public User toUser(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      User user = new User();
      user.setUsername((String) linkedHashMap.get("username"));
      user.setFirstName((String) linkedHashMap.get("firstName"));
      user.setLastName((String) linkedHashMap.get("lastName"));
      user.setMail((String) linkedHashMap.get("mail"));
      user.setCertificate((byte[]) linkedHashMap.get("certificate"));
      user.setOrganization(toOrganization(linkedHashMap.get("organization")));
      user.setMetadatas((Map<String, Object>) linkedHashMap.get("metadatas"));
      user.setAddress((Map<String, String>) linkedHashMap.get("address"));
      user.setAttributes((Map<String, Object>) linkedHashMap.get("attributes"));
      return user;
    }
    return null;
  }

  public Organization toOrganization(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      Organization organization = new Organization();
      organization.setIdentifiant((String) linkedHashMap.get("identifiant"));
      organization.setGpgkey((byte[]) linkedHashMap.get("gpgkey"));
      organization.setAddress((Map<String, String>) linkedHashMap.get("address"));
      organization.setAttributes((Map<String, Object>) linkedHashMap.get("attributes"));
      organization.setMetadatas((Map<String, Object>) linkedHashMap.get("metadatas"));
      organization.setOrganization(
          linkedHashMap.get("organization") != null
              ? toOrganization(linkedHashMap.get("organization"))
              : null);
      return organization;
    }
    return null;
  }

  public Application toApplication(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      Application application = new Application();
      application.setName((String) linkedHashMap.get("name"));
      application.setOwner((String) linkedHashMap.get("owner"));
      List<Object> listGroup =
          (linkedHashMap.get("groups") != null
              ? (List<Object>) linkedHashMap.get("groups")
              : new ArrayList<>());
      application.setGroups(
          (List<Group>)
              listGroup.stream()
                  .map(groupObject -> toGroup(groupObject))
                  .collect(Collectors.toList()));
      return application;
    }
    return null;
  }

  public Group toGroup(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      Group group = new Group();
      group.setDescription((String) linkedHashMap.get("description"));
      group.setName((String) linkedHashMap.get("name"));
      List<Object> usersList =
          (linkedHashMap.get("users") != null
              ? (List<Object>) linkedHashMap.get("users")
              : new ArrayList<>());
      group.setUsers(
          usersList.stream().map((userObject) -> toUser(object)).collect(Collectors.toList()));
      return group;
    }
    return null;
  }

  public ProviderRequest toProviderRequest(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      ProviderRequest pr = new ProviderRequest();
      pr.setAsynchronousAllowed((boolean) linkedHashMap.get("asynchronousAllowed"));
      pr.setSugoiUser(toSugoiUser(linkedHashMap.get("sugoiUser")));
      pr.setIsUrgent((boolean) linkedHashMap.get("urgent"));
      pr.setTransactionId((String) linkedHashMap.get("transactionId"));
      return pr;
    }
    return null;
  }

  public SugoiUser toSugoiUser(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      SugoiUser su = new SugoiUser();
      su.setName((String) linkedHashMap.get("name"));
      su.setRoles((List<String>) linkedHashMap.get("roles"));
      return su;
    }
    return null;
  }

  public ProviderResponse toProviderResponse(Object object) {
    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
    if (linkedHashMap != null) {
      ProviderResponse pr = new ProviderResponse();
      pr.setEntityId((String) linkedHashMap.get("entityId"));
      pr.setException((RuntimeException) linkedHashMap.get("exception"));
      pr.setRequestId((String) linkedHashMap.get("requestId"));
      pr.setStatus((ProviderResponseStatus) linkedHashMap.get("status"));
      return pr;
    }
    return null;
  }

  public Map<String, String> toMapStringString(Object object) {
    LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap) object;
    return (Map<String, String>) linkedHashMap;
  }

  public byte[] convertToBytes(Object object) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos)) {
      out.writeObject(object);
      return bos.toByteArray();
    }
  }
}
