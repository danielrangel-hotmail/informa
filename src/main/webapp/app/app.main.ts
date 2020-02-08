import "zone.js/dist/task-tracking"
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { InformaAppModule } from './app.module';
import { NgZone } from '@angular/core';

ProdConfig();

if (module['hot']) {
  module['hot'].accept();
}

platformBrowserDynamic()
  .bootstrapModule(InformaAppModule, { preserveWhitespaces: true })
  .then(moduleInstance => {
    // eslint-disable-next-line no-console
    console.log('Application started')
    // Ensure Angular destroys itself on hot reloads.
    if (window["ngRef"]) {
      window["ngRef"].destroy();
    }
    window["ngRef"] = moduleInstance;

    const ngZone = moduleInstance.injector.get(NgZone);
    setInterval(() => {
      const taskTrackingZone = (ngZone)._inner.getZoneWith("TaskTrackingZone");
      if (!taskTrackingZone) {
        throw new Error("'TaskTrackingZone' zone not found! Have you loaded 'node_modules/zone.js/dist/task-tracking.js'?");
      }
      let tasks: any[] = taskTrackingZone._properties.TaskTrackingZone.getTasksFor("macroTask");
      tasks = tasks.map((v: any) => v);
      if (tasks.length > 0) {
        // eslint-disable-next-line no-console
        console.log("ZONE pending tasks=", tasks);
      }
    }, 1000);

    // Otherwise, log the boot error
  })
  .catch(err => console.error(err));
