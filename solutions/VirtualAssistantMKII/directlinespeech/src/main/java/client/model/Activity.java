/*
 * Microsoft Bot Connector API - v3.0
 * The Bot Connector REST API allows your bot to send and receive messages to channels configured in the  [Bot Framework Developer Portal](https://dev.botframework.com). The Connector service uses industry-standard REST  and JSON over HTTPS.    Client libraries for this REST API are available. See below for a list.    Many bots will use both the Bot Connector REST API and the associated [Bot State REST API](/en-us/restapi/state). The  Bot State REST API allows a bot to store and retrieve state associated with users and conversations.    Authentication for both the Bot Connector and Bot State REST APIs is accomplished with JWT Bearer tokens, and is  described in detail in the [Connector Authentication](/en-us/restapi/authentication) document.    # Client Libraries for the Bot Connector REST API    * [Bot Builder for C#](/en-us/csharp/builder/sdkreference/)  * [Bot Builder for Node.js](/en-us/node/builder/overview/)  * Generate your own from the [Connector API Swagger file](https://raw.githubusercontent.com/Microsoft/BotBuilder/master/CSharp/Library/Microsoft.Bot.Connector.Shared/Swagger/ConnectorAPI.json)    � 2016 Microsoft
 *
 * OpenAPI spec version: v3
 * Contact: botframework@microsoft.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package client.model;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * An Activity is the basic communication type for the Bot Framework 3.0 protocol.
 */
@ApiModel(description = "An Activity is the basic communication type for the Bot Framework 3.0 protocol.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-08-29T10:06:15.114-07:00")
public class Activity {
  @SerializedName("type")
  private ActivityTypes type = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("timestamp")
  private OffsetDateTime timestamp = null;

  @SerializedName("localTimestamp")
  private OffsetDateTime localTimestamp = null;

  @SerializedName("serviceUrl")
  private String serviceUrl = null;

  @SerializedName("channelId")
  private String channelId = null;

  @SerializedName("from")
  private ChannelAccount from = null;

  @SerializedName("conversation")
  private ConversationAccount conversation = null;

  @SerializedName("recipient")
  private ChannelAccount recipient = null;

  @SerializedName("textFormat")
  private TextFormatTypes textFormat = null;

  @SerializedName("attachmentLayout")
  private AttachmentLayoutTypes attachmentLayout = null;

  @SerializedName("membersAdded")
  private List<ChannelAccount> membersAdded = null;

  @SerializedName("membersRemoved")
  private List<ChannelAccount> membersRemoved = null;

  @SerializedName("reactionsAdded")
  private List<MessageReaction> reactionsAdded = null;

  @SerializedName("reactionsRemoved")
  private List<MessageReaction> reactionsRemoved = null;

  @SerializedName("topicName")
  private String topicName = null;

  @SerializedName("historyDisclosed")
  private Boolean historyDisclosed = null;

  @SerializedName("locale")
  private String locale = null;

  @SerializedName("text")
  private String text = null;

  @SerializedName("speak")
  private String speak = null;

  @SerializedName("inputHint")
  private InputHints inputHint = null;

  @SerializedName("summary")
  private String summary = null;

  @SerializedName("suggestedActions")
  private SuggestedActions suggestedActions = null;

  @SerializedName("attachments")
  private List<Attachment> attachments = null;

  @SerializedName("entities")
  private List<Entity> entities = null;

  @SerializedName("channelData")
  private Object channelData = null;

  @SerializedName("action")
  private String action = null;

  @SerializedName("replyToId")
  private String replyToId = null;

  @SerializedName("label")
  private String label = null;

  @SerializedName("valueType")
  private String valueType = null;

  @SerializedName("value")
  private Object value = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("relatesTo")
  private ConversationReference relatesTo = null;

  @SerializedName("code")
  private EndOfConversationCodes code = null;

  @SerializedName("expiration")
  private OffsetDateTime expiration = null;

  @SerializedName("importance")
  private ActivityImportance importance = null;

  @SerializedName("deliveryMode")
  private DeliveryModes deliveryMode = null;

  @SerializedName("listenFor")
  private List<String> listenFor = null;

  @SerializedName("textHighlights")
  private List<TextHighlight> textHighlights = null;

  @SerializedName("semanticAction")
  private SemanticAction semanticAction = null;

  public Activity type(ActivityTypes type) {
    this.type = type;
    return this;
  }

   /**
   * Contains the activity type.
   * @return type
  **/
  @ApiModelProperty(value = "Contains the activity type.")
  public ActivityTypes getType() {
    return type;
  }

  public void setType(ActivityTypes type) {
    this.type = type;
  }

  public Activity id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Contains an ID that uniquely identifies the activity on the channel.
   * @return id
  **/
  @ApiModelProperty(value = "Contains an ID that uniquely identifies the activity on the channel.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Activity timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Contains the date and time that the message was sent, in UTC, expressed in ISO-8601 format.
   * @return timestamp
  **/
  @ApiModelProperty(value = "Contains the date and time that the message was sent, in UTC, expressed in ISO-8601 format.")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Activity localTimestamp(OffsetDateTime localTimestamp) {
    this.localTimestamp = localTimestamp;
    return this;
  }

   /**
   * Contains the date and time that the message was sent, in local time, expressed in ISO-8601 format.  For example, 2016-09-23T13:07:49.4714686-07:00.
   * @return localTimestamp
  **/
  @ApiModelProperty(value = "Contains the date and time that the message was sent, in local time, expressed in ISO-8601 format.  For example, 2016-09-23T13:07:49.4714686-07:00.")
  public OffsetDateTime getLocalTimestamp() {
    return localTimestamp;
  }

  public void setLocalTimestamp(OffsetDateTime localTimestamp) {
    this.localTimestamp = localTimestamp;
  }

  public Activity serviceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
    return this;
  }

   /**
   * Contains the URL that specifies the channel&#39;s service endpoint. Set by the channel.
   * @return serviceUrl
  **/
  @ApiModelProperty(value = "Contains the URL that specifies the channel's service endpoint. Set by the channel.")
  public String getServiceUrl() {
    return serviceUrl;
  }

  public void setServiceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
  }

  public Activity channelId(String channelId) {
    this.channelId = channelId;
    return this;
  }

   /**
   * Contains an ID that uniquely identifies the channel. Set by the channel.
   * @return channelId
  **/
  @ApiModelProperty(value = "Contains an ID that uniquely identifies the channel. Set by the channel.")
  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public Activity from(ChannelAccount from) {
    this.from = from;
    return this;
  }

   /**
   * Identifies the sender of the message.
   * @return from
  **/
  @ApiModelProperty(value = "Identifies the sender of the message.")
  public ChannelAccount getFrom() {
    return from;
  }

  public void setFrom(ChannelAccount from) {
    this.from = from;
  }

  public Activity conversation(ConversationAccount conversation) {
    this.conversation = conversation;
    return this;
  }

   /**
   * Identifies the conversation to which the activity belongs.
   * @return conversation
  **/
  @ApiModelProperty(value = "Identifies the conversation to which the activity belongs.")
  public ConversationAccount getConversation() {
    return conversation;
  }

  public void setConversation(ConversationAccount conversation) {
    this.conversation = conversation;
  }

  public Activity recipient(ChannelAccount recipient) {
    this.recipient = recipient;
    return this;
  }

   /**
   * Identifies the recipient of the message.
   * @return recipient
  **/
  @ApiModelProperty(value = "Identifies the recipient of the message.")
  public ChannelAccount getRecipient() {
    return recipient;
  }

  public void setRecipient(ChannelAccount recipient) {
    this.recipient = recipient;
  }

  public Activity textFormat(TextFormatTypes textFormat) {
    this.textFormat = textFormat;
    return this;
  }

   /**
   * Format of text fields Default:markdown
   * @return textFormat
  **/
  @ApiModelProperty(value = "Format of text fields Default:markdown")
  public TextFormatTypes getTextFormat() {
    return textFormat;
  }

  public void setTextFormat(TextFormatTypes textFormat) {
    this.textFormat = textFormat;
  }

  public Activity attachmentLayout(AttachmentLayoutTypes attachmentLayout) {
    this.attachmentLayout = attachmentLayout;
    return this;
  }

   /**
   * The layout hint for multiple attachments. Default: list.
   * @return attachmentLayout
  **/
  @ApiModelProperty(value = "The layout hint for multiple attachments. Default: list.")
  public AttachmentLayoutTypes getAttachmentLayout() {
    return attachmentLayout;
  }

  public void setAttachmentLayout(AttachmentLayoutTypes attachmentLayout) {
    this.attachmentLayout = attachmentLayout;
  }

  public Activity membersAdded(List<ChannelAccount> membersAdded) {
    this.membersAdded = membersAdded;
    return this;
  }

  public Activity addMembersAddedItem(ChannelAccount membersAddedItem) {
    if (this.membersAdded == null) {
      this.membersAdded = new ArrayList<ChannelAccount>();
    }
    this.membersAdded.add(membersAddedItem);
    return this;
  }

   /**
   * The collection of members added to the conversation.
   * @return membersAdded
  **/
  @ApiModelProperty(value = "The collection of members added to the conversation.")
  public List<ChannelAccount> getMembersAdded() {
    return membersAdded;
  }

  public void setMembersAdded(List<ChannelAccount> membersAdded) {
    this.membersAdded = membersAdded;
  }

  public Activity membersRemoved(List<ChannelAccount> membersRemoved) {
    this.membersRemoved = membersRemoved;
    return this;
  }

  public Activity addMembersRemovedItem(ChannelAccount membersRemovedItem) {
    if (this.membersRemoved == null) {
      this.membersRemoved = new ArrayList<ChannelAccount>();
    }
    this.membersRemoved.add(membersRemovedItem);
    return this;
  }

   /**
   * The collection of members removed from the conversation.
   * @return membersRemoved
  **/
  @ApiModelProperty(value = "The collection of members removed from the conversation.")
  public List<ChannelAccount> getMembersRemoved() {
    return membersRemoved;
  }

  public void setMembersRemoved(List<ChannelAccount> membersRemoved) {
    this.membersRemoved = membersRemoved;
  }

  public Activity reactionsAdded(List<MessageReaction> reactionsAdded) {
    this.reactionsAdded = reactionsAdded;
    return this;
  }

  public Activity addReactionsAddedItem(MessageReaction reactionsAddedItem) {
    if (this.reactionsAdded == null) {
      this.reactionsAdded = new ArrayList<MessageReaction>();
    }
    this.reactionsAdded.add(reactionsAddedItem);
    return this;
  }

   /**
   * The collection of reactions added to the conversation.
   * @return reactionsAdded
  **/
  @ApiModelProperty(value = "The collection of reactions added to the conversation.")
  public List<MessageReaction> getReactionsAdded() {
    return reactionsAdded;
  }

  public void setReactionsAdded(List<MessageReaction> reactionsAdded) {
    this.reactionsAdded = reactionsAdded;
  }

  public Activity reactionsRemoved(List<MessageReaction> reactionsRemoved) {
    this.reactionsRemoved = reactionsRemoved;
    return this;
  }

  public Activity addReactionsRemovedItem(MessageReaction reactionsRemovedItem) {
    if (this.reactionsRemoved == null) {
      this.reactionsRemoved = new ArrayList<MessageReaction>();
    }
    this.reactionsRemoved.add(reactionsRemovedItem);
    return this;
  }

   /**
   * The collection of reactions removed from the conversation.
   * @return reactionsRemoved
  **/
  @ApiModelProperty(value = "The collection of reactions removed from the conversation.")
  public List<MessageReaction> getReactionsRemoved() {
    return reactionsRemoved;
  }

  public void setReactionsRemoved(List<MessageReaction> reactionsRemoved) {
    this.reactionsRemoved = reactionsRemoved;
  }

  public Activity topicName(String topicName) {
    this.topicName = topicName;
    return this;
  }

   /**
   * The updated topic name of the conversation.
   * @return topicName
  **/
  @ApiModelProperty(value = "The updated topic name of the conversation.")
  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public Activity historyDisclosed(Boolean historyDisclosed) {
    this.historyDisclosed = historyDisclosed;
    return this;
  }

   /**
   * Indicates whether the prior history of the channel is disclosed.
   * @return historyDisclosed
  **/
  @ApiModelProperty(value = "Indicates whether the prior history of the channel is disclosed.")
  public Boolean isHistoryDisclosed() {
    return historyDisclosed;
  }

  public void setHistoryDisclosed(Boolean historyDisclosed) {
    this.historyDisclosed = historyDisclosed;
  }

  public Activity locale(String locale) {
    this.locale = locale;
    return this;
  }

   /**
   * A locale name for the contents of the text field.  The locale name is a combination of an ISO 639 two- or three-letter culture code associated with a language  and an ISO 3166 two-letter subculture code associated with a country or region.  The locale name can also correspond to a valid BCP-47 language tag.
   * @return locale
  **/
  @ApiModelProperty(value = "A locale name for the contents of the text field.  The locale name is a combination of an ISO 639 two- or three-letter culture code associated with a language  and an ISO 3166 two-letter subculture code associated with a country or region.  The locale name can also correspond to a valid BCP-47 language tag.")
  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public Activity text(String text) {
    this.text = text;
    return this;
  }

   /**
   * The text content of the message.
   * @return text
  **/
  @ApiModelProperty(value = "The text content of the message.")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Activity speak(String speak) {
    this.speak = speak;
    return this;
  }

   /**
   * The text to speak.
   * @return speak
  **/
  @ApiModelProperty(value = "The text to speak.")
  public String getSpeak() {
    return speak;
  }

  public void setSpeak(String speak) {
    this.speak = speak;
  }

  public Activity inputHint(InputHints inputHint) {
    this.inputHint = inputHint;
    return this;
  }

   /**
   * Indicates whether your bot is accepting,  expecting, or ignoring user input after the message is delivered to the client.
   * @return inputHint
  **/
  @ApiModelProperty(value = "Indicates whether your bot is accepting,  expecting, or ignoring user input after the message is delivered to the client.")
  public InputHints getInputHint() {
    return inputHint;
  }

  public void setInputHint(InputHints inputHint) {
    this.inputHint = inputHint;
  }

  public Activity summary(String summary) {
    this.summary = summary;
    return this;
  }

   /**
   * The text to display if the channel cannot render cards.
   * @return summary
  **/
  @ApiModelProperty(value = "The text to display if the channel cannot render cards.")
  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Activity suggestedActions(SuggestedActions suggestedActions) {
    this.suggestedActions = suggestedActions;
    return this;
  }

   /**
   * The suggested actions for the activity.
   * @return suggestedActions
  **/
  @ApiModelProperty(value = "The suggested actions for the activity.")
  public SuggestedActions getSuggestedActions() {
    return suggestedActions;
  }

  public void setSuggestedActions(SuggestedActions suggestedActions) {
    this.suggestedActions = suggestedActions;
  }

  public Activity attachments(List<Attachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public Activity addAttachmentsItem(Attachment attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<Attachment>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

   /**
   * Attachments
   * @return attachments
  **/
  @ApiModelProperty(value = "Attachments")
  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public Activity entities(List<Entity> entities) {
    this.entities = entities;
    return this;
  }

  public Activity addEntitiesItem(Entity entitiesItem) {
    if (this.entities == null) {
      this.entities = new ArrayList<Entity>();
    }
    this.entities.add(entitiesItem);
    return this;
  }

   /**
   * Represents the entities that were mentioned in the message.
   * @return entities
  **/
  @ApiModelProperty(value = "Represents the entities that were mentioned in the message.")
  public List<Entity> getEntities() {
    return entities;
  }

  public void setEntities(List<Entity> entities) {
    this.entities = entities;
  }

  public Activity channelData(Object channelData) {
    this.channelData = channelData;
    return this;
  }

   /**
   * Contains channel-specific content.
   * @return channelData
  **/
  @ApiModelProperty(value = "Contains channel-specific content.")
  public Object getChannelData() {
    return channelData;
  }

  public void setChannelData(Object channelData) {
    this.channelData = channelData;
  }

  public Activity action(String action) {
    this.action = action;
    return this;
  }

   /**
   * Indicates whether the recipient of a contactRelationUpdate was added or removed from the sender&#39;s contact list.
   * @return action
  **/
  @ApiModelProperty(value = "Indicates whether the recipient of a contactRelationUpdate was added or removed from the sender's contact list.")
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Activity replyToId(String replyToId) {
    this.replyToId = replyToId;
    return this;
  }

   /**
   * Contains the ID of the message to which this message is a reply.
   * @return replyToId
  **/
  @ApiModelProperty(value = "Contains the ID of the message to which this message is a reply.")
  public String getReplyToId() {
    return replyToId;
  }

  public void setReplyToId(String replyToId) {
    this.replyToId = replyToId;
  }

  public Activity label(String label) {
    this.label = label;
    return this;
  }

   /**
   * A descriptive label for the activity.
   * @return label
  **/
  @ApiModelProperty(value = "A descriptive label for the activity.")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Activity valueType(String valueType) {
    this.valueType = valueType;
    return this;
  }

   /**
   * The type of the activity&#39;s value object.
   * @return valueType
  **/
  @ApiModelProperty(value = "The type of the activity's value object.")
  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public Activity value(Object value) {
    this.value = value;
    return this;
  }

   /**
   * A value that is associated with the activity.
   * @return value
  **/
  @ApiModelProperty(value = "A value that is associated with the activity.")
  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Activity name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the operation associated with an invoke or event activity.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the operation associated with an invoke or event activity.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Activity relatesTo(ConversationReference relatesTo) {
    this.relatesTo = relatesTo;
    return this;
  }

   /**
   * A reference to another conversation or activity.
   * @return relatesTo
  **/
  @ApiModelProperty(value = "A reference to another conversation or activity.")
  public ConversationReference getRelatesTo() {
    return relatesTo;
  }

  public void setRelatesTo(ConversationReference relatesTo) {
    this.relatesTo = relatesTo;
  }

  public Activity code(EndOfConversationCodes code) {
    this.code = code;
    return this;
  }

   /**
   * The a code for endOfConversation activities that indicates why the conversation ended.
   * @return code
  **/
  @ApiModelProperty(value = "The a code for endOfConversation activities that indicates why the conversation ended.")
  public EndOfConversationCodes getCode() {
    return code;
  }

  public void setCode(EndOfConversationCodes code) {
    this.code = code;
  }

  public Activity expiration(OffsetDateTime expiration) {
    this.expiration = expiration;
    return this;
  }

   /**
   * The time at which the activity should be considered to be \&quot;expired\&quot; and should not be presented to the recipient.
   * @return expiration
  **/
  @ApiModelProperty(value = "The time at which the activity should be considered to be \"expired\" and should not be presented to the recipient.")
  public OffsetDateTime getExpiration() {
    return expiration;
  }

  public void setExpiration(OffsetDateTime expiration) {
    this.expiration = expiration;
  }

  public Activity importance(ActivityImportance importance) {
    this.importance = importance;
    return this;
  }

   /**
   * The importance of the activity.
   * @return importance
  **/
  @ApiModelProperty(value = "The importance of the activity.")
  public ActivityImportance getImportance() {
    return importance;
  }

  public void setImportance(ActivityImportance importance) {
    this.importance = importance;
  }

  public Activity deliveryMode(DeliveryModes deliveryMode) {
    this.deliveryMode = deliveryMode;
    return this;
  }

   /**
   * A delivery hint to  signal to the recipient alternate delivery paths for the activity.  The default delivery mode is \&quot;default\&quot;.
   * @return deliveryMode
  **/
  @ApiModelProperty(value = "A delivery hint to  signal to the recipient alternate delivery paths for the activity.  The default delivery mode is \"default\".")
  public DeliveryModes getDeliveryMode() {
    return deliveryMode;
  }

  public void setDeliveryMode(DeliveryModes deliveryMode) {
    this.deliveryMode = deliveryMode;
  }

  public Activity listenFor(List<String> listenFor) {
    this.listenFor = listenFor;
    return this;
  }

  public Activity addListenForItem(String listenForItem) {
    if (this.listenFor == null) {
      this.listenFor = new ArrayList<String>();
    }
    this.listenFor.add(listenForItem);
    return this;
  }

   /**
   * List of phrases and references that speech and language priming systems should listen for
   * @return listenFor
  **/
  @ApiModelProperty(value = "List of phrases and references that speech and language priming systems should listen for")
  public List<String> getListenFor() {
    return listenFor;
  }

  public void setListenFor(List<String> listenFor) {
    this.listenFor = listenFor;
  }

  public Activity textHighlights(List<TextHighlight> textHighlights) {
    this.textHighlights = textHighlights;
    return this;
  }

  public Activity addTextHighlightsItem(TextHighlight textHighlightsItem) {
    if (this.textHighlights == null) {
      this.textHighlights = new ArrayList<TextHighlight>();
    }
    this.textHighlights.add(textHighlightsItem);
    return this;
  }

   /**
   * The collection of text fragments to highlight when the activity contains a ReplyToId value.
   * @return textHighlights
  **/
  @ApiModelProperty(value = "The collection of text fragments to highlight when the activity contains a ReplyToId value.")
  public List<TextHighlight> getTextHighlights() {
    return textHighlights;
  }

  public void setTextHighlights(List<TextHighlight> textHighlights) {
    this.textHighlights = textHighlights;
  }

  public Activity semanticAction(SemanticAction semanticAction) {
    this.semanticAction = semanticAction;
    return this;
  }

   /**
   * An optional programmatic action accompanying this request
   * @return semanticAction
  **/
  @ApiModelProperty(value = "An optional programmatic action accompanying this request")
  public SemanticAction getSemanticAction() {
    return semanticAction;
  }

  public void setSemanticAction(SemanticAction semanticAction) {
    this.semanticAction = semanticAction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Activity activity = (Activity) o;
    return Objects.equals(this.type, activity.type) &&
        Objects.equals(this.id, activity.id) &&
        Objects.equals(this.timestamp, activity.timestamp) &&
        Objects.equals(this.localTimestamp, activity.localTimestamp) &&
        Objects.equals(this.serviceUrl, activity.serviceUrl) &&
        Objects.equals(this.channelId, activity.channelId) &&
        Objects.equals(this.from, activity.from) &&
        Objects.equals(this.conversation, activity.conversation) &&
        Objects.equals(this.recipient, activity.recipient) &&
        Objects.equals(this.textFormat, activity.textFormat) &&
        Objects.equals(this.attachmentLayout, activity.attachmentLayout) &&
        Objects.equals(this.membersAdded, activity.membersAdded) &&
        Objects.equals(this.membersRemoved, activity.membersRemoved) &&
        Objects.equals(this.reactionsAdded, activity.reactionsAdded) &&
        Objects.equals(this.reactionsRemoved, activity.reactionsRemoved) &&
        Objects.equals(this.topicName, activity.topicName) &&
        Objects.equals(this.historyDisclosed, activity.historyDisclosed) &&
        Objects.equals(this.locale, activity.locale) &&
        Objects.equals(this.text, activity.text) &&
        Objects.equals(this.speak, activity.speak) &&
        Objects.equals(this.inputHint, activity.inputHint) &&
        Objects.equals(this.summary, activity.summary) &&
        Objects.equals(this.suggestedActions, activity.suggestedActions) &&
        Objects.equals(this.attachments, activity.attachments) &&
        Objects.equals(this.entities, activity.entities) &&
        Objects.equals(this.channelData, activity.channelData) &&
        Objects.equals(this.action, activity.action) &&
        Objects.equals(this.replyToId, activity.replyToId) &&
        Objects.equals(this.label, activity.label) &&
        Objects.equals(this.valueType, activity.valueType) &&
        Objects.equals(this.value, activity.value) &&
        Objects.equals(this.name, activity.name) &&
        Objects.equals(this.relatesTo, activity.relatesTo) &&
        Objects.equals(this.code, activity.code) &&
        Objects.equals(this.expiration, activity.expiration) &&
        Objects.equals(this.importance, activity.importance) &&
        Objects.equals(this.deliveryMode, activity.deliveryMode) &&
        Objects.equals(this.listenFor, activity.listenFor) &&
        Objects.equals(this.textHighlights, activity.textHighlights) &&
        Objects.equals(this.semanticAction, activity.semanticAction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, id, timestamp, localTimestamp, serviceUrl, channelId, from, conversation, recipient, textFormat, attachmentLayout, membersAdded, membersRemoved, reactionsAdded, reactionsRemoved, topicName, historyDisclosed, locale, text, speak, inputHint, summary, suggestedActions, attachments, entities, channelData, action, replyToId, label, valueType, value, name, relatesTo, code, expiration, importance, deliveryMode, listenFor, textHighlights, semanticAction);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Activity {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    localTimestamp: ").append(toIndentedString(localTimestamp)).append("\n");
    sb.append("    serviceUrl: ").append(toIndentedString(serviceUrl)).append("\n");
    sb.append("    channelId: ").append(toIndentedString(channelId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    conversation: ").append(toIndentedString(conversation)).append("\n");
    sb.append("    recipient: ").append(toIndentedString(recipient)).append("\n");
    sb.append("    textFormat: ").append(toIndentedString(textFormat)).append("\n");
    sb.append("    attachmentLayout: ").append(toIndentedString(attachmentLayout)).append("\n");
    sb.append("    membersAdded: ").append(toIndentedString(membersAdded)).append("\n");
    sb.append("    membersRemoved: ").append(toIndentedString(membersRemoved)).append("\n");
    sb.append("    reactionsAdded: ").append(toIndentedString(reactionsAdded)).append("\n");
    sb.append("    reactionsRemoved: ").append(toIndentedString(reactionsRemoved)).append("\n");
    sb.append("    topicName: ").append(toIndentedString(topicName)).append("\n");
    sb.append("    historyDisclosed: ").append(toIndentedString(historyDisclosed)).append("\n");
    sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    speak: ").append(toIndentedString(speak)).append("\n");
    sb.append("    inputHint: ").append(toIndentedString(inputHint)).append("\n");
    sb.append("    summary: ").append(toIndentedString(summary)).append("\n");
    sb.append("    suggestedActions: ").append(toIndentedString(suggestedActions)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
    sb.append("    channelData: ").append(toIndentedString(channelData)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    replyToId: ").append(toIndentedString(replyToId)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    valueType: ").append(toIndentedString(valueType)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    relatesTo: ").append(toIndentedString(relatesTo)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    expiration: ").append(toIndentedString(expiration)).append("\n");
    sb.append("    importance: ").append(toIndentedString(importance)).append("\n");
    sb.append("    deliveryMode: ").append(toIndentedString(deliveryMode)).append("\n");
    sb.append("    listenFor: ").append(toIndentedString(listenFor)).append("\n");
    sb.append("    textHighlights: ").append(toIndentedString(textHighlights)).append("\n");
    sb.append("    semanticAction: ").append(toIndentedString(semanticAction)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

