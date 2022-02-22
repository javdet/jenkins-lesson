data "aws_ami" "ubuntu2004" {
  most_recent = true
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-focal-20.04-amd64-server-20220131"]
  }
  owners = ["amazon", "099720109477"]
}

resource "aws_instance" "rabbitmq" {
    ami                          = data.aws_ami.ubuntu2004.id
    associate_public_ip_address  = false
    ebs_optimized                = false
    instance_type                = "t3a.nano"
    key_name                     = "jenkins"
    subnet_id                    = "subnet-9d9b98e0"

    root_block_device {
        delete_on_termination = true
        encrypted             = false
        volume_size           = 20
        volume_type           = "standard"
    }
}