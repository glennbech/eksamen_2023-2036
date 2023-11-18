resource "aws_cloudwatch_dashboard" "main"{
  dashboard_name = var.student_name
  dashboard_body = <<DASHBOARD
{
  "widgets": [
    {
      "type": "metric",
      "x": 0,
      "y": 0,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          [
            "${var.student_name}",
            "FaceCoverViolation",
            "Period",
            86400,
            "Stat",
            "Sum"
          ],
          [
            "${var.student_name}",
            "HandCoverViolation",
            "Period",
            86400,
            "Stat",
            "Sum"
          ],
          [
            "${var.student_name}",
            "HeadCoverViolation",
            "Period",
            86400,
            "Stat",
            "Sum"
          ]
        ],
        "period": 86400,
        "stat": "Sum",
        "region": "eu-west-1",
        "title": "Daily PPE Violations Summary"
      }
    }
  ]
}
DASHBOARD
}