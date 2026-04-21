import { Routes } from '@angular/router';
import { EndpointFormComponent } from './pages/endpoint-form/endpoint-form';
import { EndpointResultComponent } from './pages/endpoint-result/endpoint-result';
import { LandingComponent } from './pages/landing/landing';
import { ModuleHomeComponent } from './pages/module-home/module-home';
import { NotFoundComponent } from './pages/not-found/not-found';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'member/:id', component: ModuleHomeComponent, canActivate: [authGuard] },
  { path: 'endpoint-form/:moduleId/:endpointId', component: EndpointFormComponent, canActivate: [authGuard] },
  { path: 'result', component: EndpointResultComponent, canActivate: [authGuard] },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];
