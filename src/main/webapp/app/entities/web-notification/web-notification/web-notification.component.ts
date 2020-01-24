import { Component, OnInit } from '@angular/core';
import { SwPush } from '@angular/service-worker';
import {PushSubscriptionService} from 'app/entities/push-subscription/push-subscription.service';

@Component({
  selector: 'jhi-web-notification',
  templateUrl: './web-notification.component.html',
  styleUrls: ['./web-notification.component.scss']
})
export class WebNotificationComponent implements OnInit {

  readonly VAPID_PUBLIC_KEY = "BKzVYXkDTzcYQfur-mkNoSuP08tdwNqivtKmVMg1Nfdv8x8nP-SWdWyTClaXXf9QXN_MqszxrTB50mdWm_8WC4U";

  constructor(private swPush: SwPush,
              private pushSubscriptionService: PushSubscriptionService) { }

  ngOnInit(): void {
  }

  pedeNotificacao(): void {
    // navigator.serviceWorker.getRegistration().then((reg) => {
    //   // eslint-disable-next-line no-console
    //   console.log(`registration ${Notification.permission}`);
    //   reg!.showNotification('Hello world!');
    // });
    //
    this.swPush.isEnabled
    this.swPush.requestSubscription({
      serverPublicKey: this.VAPID_PUBLIC_KEY
    })
    .then(sub => this.pushSubscriptionService.addPushSubscriber(sub).subscribe())
    .catch(err => console.error("Could not subscribe to notifications", err));
  }
}
