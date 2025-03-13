import { NgFor } from '@angular/common';
import { Component, OnDestroy, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DeviceService } from '../device.service';
import { ToastrService } from 'ngx-toastr';
import { Device } from './device.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-device',
  standalone: true,
  imports: [FormsModule, NgFor],
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.scss']
})
export class DeviceComponent implements OnDestroy {
  private toastr = inject(ToastrService); 

  showForm = false;
  devices: Device[] = []; 
  deviceId: number = NaN;
  newDevice: Device = { id: 0, name: '', deviceType: '' }; 

  private subscriptions: Subscription[] = []; 

  constructor(private deviceService: DeviceService) {}

  showToastSuccess(message: string) {
    this.toastr.success(message, 'Success');
  }

  showToastError(message: string) {
    this.toastr.error(message, 'Error');
  }

  fetchAllDevices() {
    const sub = this.deviceService.getAllDevices().subscribe(
      (response: Device[]) => {
        this.devices = response;
        console.log("Devices fetched successfully:", this.devices);
        this.showToastSuccess("Devices fetched successfully!");
      },
      (error) => {
        console.error("Error fetching devices:", error);
        this.showToastError("Error fetching devices!");
      }
    );
    this.subscriptions.push(sub);
  }

  hideForm() {
    this.showForm = false;
    this.newDevice = { id: 0, name: '', deviceType: '' };
    this.deviceId = NaN;
  }

  onSubmitSave() {
    const sub = this.deviceService.saveDevice(this.newDevice).subscribe(
      (response: Device) => {
        console.log('Device Saved:', response);
        this.showToastSuccess("Device saved successfully!");
        this.fetchAllDevices();
        this.hideForm();
      },
      (error) => {
        console.error('Error saving device:', error);
        this.showToastError("Error saving device!");
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitModify() {
    const sub = this.deviceService.modifyDevice(this.deviceId, this.newDevice).subscribe(
      (response: Device) => {
        console.log('Device Modified:', response);
        this.showToastSuccess("Device modified successfully!");
        this.fetchAllDevices();
        this.hideForm();
      },
      (error) => {
        console.error("Error modifying device: ", error);
        this.showToastError("Error modifying device!");
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitGet() {
    const sub = this.deviceService.getDevice(this.deviceId).subscribe(
      (response: Device) => {
        console.log("Received Device:", response);
        this.newDevice = response; 
        this.showForm = true;
        this.showToastSuccess("Device retrieved successfully!");
      },
      (error) => {
        console.error("Error fetching device", error);
        this.showToastError("Error fetching device!");
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitDelete() {
    const sub = this.deviceService.deleteDevice(this.deviceId).subscribe(
      (response) => {
        console.log("Device Deleted", response);
        this.showToastSuccess("Device deleted successfully!");
        this.fetchAllDevices();
        this.hideForm();
      },
      (error) => {
        console.error("Error deleting device", error);
        this.showToastError("Error deleting device!");
      }
    );
    this.subscriptions.push(sub);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    console.log("All subscriptions have been unsubscribed.");
  }
}