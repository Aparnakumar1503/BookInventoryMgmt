import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';

export const authGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);
  const moduleId = route.paramMap.get('id') ?? route.paramMap.get('moduleId');

  if (!authService.isAuthenticated()) {
    notificationService.error('Login is required before opening protected endpoints.');
    return router.createUrlTree(moduleId ? ['/login', moduleId] : ['/modules']);
  }

  if (moduleId && !authService.canAccessModule(moduleId)) {
    notificationService.error('This login does not have access to the selected module.');
    return router.createUrlTree(['/modules']);
  }

  return true;
};
