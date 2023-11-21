output "alarm_arn" {
  value = aws_cloudwatch_metric_alarm.alarm.arn
}

output "sns_topic_arn" {
  value = aws_sns_topic.topic.arn
}
