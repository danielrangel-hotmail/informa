import { Component, OnInit } from '@angular/core';
import { SwPush } from '@angular/service-worker';
import { WebNotificationService } from './web-notification.service';

@Component({
  selector: 'jhi-web-notification',
  templateUrl: './web-notification.component.html',
  styleUrls: ['./web-notification.component.scss']
})
export class WebNotificationComponent implements OnInit {

  readonly VAPID_PUBLIC_KEY = "BKzVYXkDTzcYQfur-mkNoSuP08tdwNqivtKmVMg1Nfdv8x8nP-SWdWyTClaXXf9QXN_MqszxrTB50mdWm_8WC4U";

  constructor(private swPush: SwPush,
              private webNotification: WebNotificationService) { }

  ngOnInit(): void {
  }

  pedeNotificacao(): void {
    this.swPush.requestSubscription({
      serverPublicKey: this.VAPID_PUBLIC_KEY
    })
    .then(sub => this.webNotification.addPushSubscriber(sub).subscribe())
    .catch(err => console.error("Could not subscribe to notifications", err));
  }
}
