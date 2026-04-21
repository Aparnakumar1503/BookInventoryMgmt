import { Routes } from '@angular/router';
import { EndpointFormComponent } from './pages/endpoint-form/endpoint-form';
import { EndpointResultComponent } from './pages/endpoint-result/endpoint-result';
import { LandingComponent } from './pages/landing/landing';
import { ModuleHomeComponent } from './pages/module-home/module-home';
import { NotFoundComponent } from './pages/not-found/not-found';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'module/:id', component: ModuleHomeComponent },
  { path: 'endpoint-form/:moduleId/:endpointId', component: EndpointFormComponent },
  { path: 'result', component: EndpointResultComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];
