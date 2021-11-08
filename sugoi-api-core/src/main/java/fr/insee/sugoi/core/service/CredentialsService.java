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
package fr.insee.sugoi.core.service;

import fr.insee.sugoi.core.exceptions.PasswordPolicyNotMetException;
import fr.insee.sugoi.core.exceptions.RealmNotFoundException;
import fr.insee.sugoi.core.exceptions.UserNotFoundException;
import fr.insee.sugoi.core.model.ProviderRequest;
import fr.insee.sugoi.core.model.ProviderResponse;
import fr.insee.sugoi.model.PasswordChangeRequest;
import java.util.Map;

public interface CredentialsService {

  /**
   * Reinit password of the user according to the realm policy.
   *
   * @param realm
   * @param userStorage
   * @param userId
   * @param pcr
   * @throws RealmNotFoundException if realm doesn't exist
   * @throws UserNotFoundException if user doesn't exist in realm
   */
  ProviderResponse reinitPassword(
      String realm,
      String userStorage,
      String userId,
      PasswordChangeRequest pcr,
      ProviderRequest providerRequest);

  /**
   * Change the password of an user, check if the new password meets the realm password policy and
   * change it
   *
   * @param realm
   * @param userStorage
   * @param userId
   * @param oldPassword the password is changed only if the oldPassword match the current user
   *     password
   * @param newPassword password to set to the user
   * @throws RealmNotFoundException if realm doesn't exist
   * @throws UserNotFoundException if user doesn't exist in realm
   * @throws PasswordPolicyNotMetException if password doesn't meet the password policy set for the
   *     realm
   */
  ProviderResponse changePassword(
      String realm,
      String userStorage,
      String userId,
      String oldPassword,
      String newPassword,
      ProviderRequest providerRequest);

  /**
   * Init the password of an user, check if the provided password met the realm password policy and
   * set it
   *
   * @param realm
   * @param userStorage
   * @param userId
   * @param pcr
   * @throws RealmNotFoundException if realm doesn't exist
   * @throws UserNotFoundException if user doesn't exist in realm
   * @throws PasswordPolicyNotMetException if password doesn't meet the password policy set for the
   *     realm
   */
  ProviderResponse initPassword(
      String realm,
      String userStorage,
      String userId,
      PasswordChangeRequest pcr,
      ProviderRequest providerRequest);

  /**
   * Check if the provided credentials allow to authenticate the current user
   *
   * @param realm
   * @param userStorage
   * @param userName
   * @param password
   * @throws UserNotFoundException if user doesn't exist in realm
   * @return true if provided credentials are valid for user else false
   */
  boolean validateCredential(String realm, String userStorage, String userName, String password);

  boolean sendLogin(String realm, String userStorage, String id, Map<String, String> properties);
}
