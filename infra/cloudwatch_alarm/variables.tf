variable "alarm_name" {
  type = string
  description = "ppe.violations.total"
}

variable "namespace" {
  type = string
  description = "namespace for the CloudWatch metric"
  default = "scanTime-2036"
}

variable "metric_name" {
  type = string
  description = "The name of the CloudWatch metric"
  default = "ppe.violations.total.value"
}

variable "comparison_operator" {
  type = string
  description = "The comparison operator for the alarm"
  default = "GreaterThanThreshold"
}

variable "threshold" {
  type = number
  description = "The threshold for the alarm"
  default = 5
}

variable "evaluation_periods" {
  type = number
  description = "The number of periods over which data is compared to the specified threshold"
  default = 2
}

variable "period" {
  type = number
  description = "The number of seconds, over which the statistic is applied"
  default = 300 // 5 minutes
}

variable "statistic" {
  type = string
  description = "The statistic to apply to the alarm's associated metric"
  default = "Sum"
}

variable "alarm_description" {
  type = string
  description = "The description for the alarm"
  default = "Alarm when the total number of PPE violations exceeds the threshold"
}

variable "topic_name" {
  type = string
  description = "The name of the SNS topic for the alarm"
  default = "alarm-topic-2036"
}

variable "alarm_email" {
  type = string
  description = "The email address to which notifications will be sent"
}
