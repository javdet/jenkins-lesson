terraform {
  required_version = ">= 1.1.5"
  backend "local" {
    path = "./terraform.tfstate"
  }
}
