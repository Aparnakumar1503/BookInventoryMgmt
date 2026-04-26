# Frontend Structure

This frontend is organized so the main ideas are easy to find:

- `core/`
  - shared app logic
  - services, models, route guards, interceptors, reusable data, and small utilities
- `features/`
  - page-level screens such as landing, modules, login, endpoint form, and response page
- `layout/`
  - app shell pieces like navbar and footer
- `modules/`
  - endpoint configuration for each business module
  - this is where endpoint names, paths, request fields, and UI grouping start
- `shared/`
  - reusable UI pieces like response viewers and spinners

## Simple page flow

1. `team-directory/pages/landing`
   - public landing page
2. `modules`
   - module catalog
3. `login`
   - module sign-in page
4. `member-workspace`
   - module overview and endpoint list
5. `endpoint-executor`
   - request form page
6. `result-viewer`
   - dedicated response page with Preview, Table, and JSON

## Where to edit common things

- Branding and top navigation:
  - `layout/navbar/`
- Landing page:
  - `features/team-directory/pages/landing/`
- Module catalog:
  - `features/modules/`
- Login screen:
  - `features/login/`
- Endpoint request page:
  - `features/endpoint-executor/pages/endpoint-form/`
- Response page:
  - `features/result-viewer/pages/endpoint-result/`
- Endpoint definitions:
  - `modules/*/data/`

## Styling note

This app is being kept beginner-friendly by preferring:

- plain component `.css` files for active pages
- Tailwind utility classes in templates where helpful
- small reusable helpers in `core/utils`

That means you can usually look in just:

- the component `.ts`
- the component `.html`
- the component `.css`

to understand one screen.
