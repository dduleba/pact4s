/*
 * Copyright 2021 io.github.jbwheatley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pact4s
package provider

final class PublishVerificationResults private (
    val providerVersion: String,
    val providerTags: Option[ProviderTags],
    val providerBranch: Option[Branch]
)

object PublishVerificationResults {
  @deprecated("use ProviderTags(..) or ProviderTags.fromList(..) rather than List[String]", "0.0.19")
  def apply(providerVersion: String, providerTags: List[String]): PublishVerificationResults =
    new PublishVerificationResults(providerVersion, ProviderTags.fromList(providerTags), None)

  def apply(providerVersion: String): PublishVerificationResults =
    new PublishVerificationResults(providerVersion, None, None)

  def apply(providerVersion: String, providerTags: ProviderTags): PublishVerificationResults =
    new PublishVerificationResults(providerVersion, Some(providerTags), None)

  @deprecated(
    "Set provider branch at PactVerifier.verifyPacts() instead as it can be used for the matchingBranch selector",
    "0.5.0"
  )
  def apply(providerVersion: String, providerBranch: Branch): PublishVerificationResults =
    new PublishVerificationResults(providerVersion, None, Some(providerBranch))
}
