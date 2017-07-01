package slack.main.scala


case class SlackDetails( channelName: String, body: String, user: Option[String] = Some("Bot"))
