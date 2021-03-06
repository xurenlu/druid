/*
 * Druid - a distributed column store.
 *  Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.druid.storage.azure;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;
import io.druid.segment.loading.LoadSpec;
import io.druid.segment.loading.SegmentLoadingException;

import java.io.File;

@JsonTypeName(AzureStorageDruidModule.SCHEME)
public class AzureLoadSpec implements LoadSpec
{
  @JsonProperty
  private final String storageDir;

  private final AzureDataSegmentPuller puller;

  @JsonCreator
  public AzureLoadSpec(
      @JacksonInject AzureDataSegmentPuller puller,
      @JsonProperty("storageDir") String storageDir
  )
  {
    Preconditions.checkNotNull(storageDir);
    this.storageDir = storageDir;
    this.puller = puller;
  }

  @Override
  public LoadSpecResult loadSegment(File file) throws SegmentLoadingException
  {
    return new LoadSpecResult(puller.getSegmentFiles(storageDir, file).size());
  }
}
