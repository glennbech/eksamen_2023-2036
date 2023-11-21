resource "aws_cloudwatch_metric_alarm" "alarm" {
  alarm_name          = var.alarm_name
  namespace           = var.namespace
  metric_name         = var.metric_name
  comparison_operator = var.comparison_operator
  threshold           = var.threshold
  evaluation_periods  = var.evaluation_periods
  period              = var.period
  statistic           = var.statistic
  alarm_description   = var.alarm_description
  alarm_actions       = [aws_sns_topic.topic.arn]
  region              = var.region
}

resource "aws_sns_topic" "topic" {
  name = var.topic_name
}

resource "aws_sns_topic_subscription" "subscription" {
  topic_arn = aws_sns_topic.topic.arn
  protocol  = "email"
  endpoint  = var.alarm_email
}



