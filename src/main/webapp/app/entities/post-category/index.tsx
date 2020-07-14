import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PostCategory from './post-category';
import PostCategoryDetail from './post-category-detail';
import PostCategoryUpdate from './post-category-update';
import PostCategoryDeleteDialog from './post-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PostCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PostCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PostCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={PostCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PostCategoryDeleteDialog} />
  </>
);

export default Routes;
