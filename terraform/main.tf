resource "yandex_compute_instance" "manager" {
  count = 3

  name        = format("manager-%02d", count.index + 1)
  hostname    = format("manager-%02d", count.index + 1)
  description = format("manager-%02d", count.index + 1)
  folder_id   = var.folder_id
  zone        = var.zone
  platform_id = "standard-v2"

  allow_stopping_for_update = true

  resources {
    cores         = 2
    core_fraction = 100
    memory        = 4
  }

  boot_disk {
    initialize_params {
      image_id = data.yandex_compute_image.ubuntu_2004.id
      size     = 30
      type     = "network-ssd"
    }
  }

  network_interface {
    subnet_id          = var.subnet
    nat                = true
  }
  
}
