import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './post-category.reducer';
import { IPostCategory } from 'app/shared/model/post-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPostCategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PostCategory = (props: IPostCategoryProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { postCategoryList, match, loading } = props;
  return (
    <div>
      <h2 id="post-category-heading">
        <Translate contentKey="listitApp.postCategory.home.title">Post Categories</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="listitApp.postCategory.home.createLabel">Create new Post Category</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {postCategoryList && postCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.postCategory.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.postCategory.isEnabled">Is Enabled</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.postCategory.maxImages">Max Images</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.postCategory.validDays">Valid Days</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.postCategory.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postCategoryList.map((postCategory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${postCategory.id}`} color="link" size="sm">
                      {postCategory.id}
                    </Button>
                  </td>
                  <td>{postCategory.name}</td>
                  <td>{postCategory.isEnabled ? 'true' : 'false'}</td>
                  <td>{postCategory.maxImages}</td>
                  <td>{postCategory.validDays}</td>
                  <td>
                    {postCategory.categoryId ? <Link to={`post-category/${postCategory.categoryId}`}>{postCategory.categoryId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${postCategory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${postCategory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${postCategory.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="listitApp.postCategory.home.notFound">No Post Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ postCategory }: IRootState) => ({
  postCategoryList: postCategory.entities,
  loading: postCategory.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostCategory);
