import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getPostCategories } from 'app/entities/post-category/post-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './post-category.reducer';
import { IPostCategory } from 'app/shared/model/post-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPostCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PostCategoryUpdate = (props: IPostCategoryUpdateProps) => {
  const [categoryId, setCategoryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { postCategoryEntity, postCategories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/post-category');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPostCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...postCategoryEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="listitApp.postCategory.home.createOrEditLabel">
            <Translate contentKey="listitApp.postCategory.home.createOrEditLabel">Create or edit a PostCategory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : postCategoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="post-category-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="post-category-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="post-category-name">
                  <Translate contentKey="listitApp.postCategory.name">Name</Translate>
                </Label>
                <AvField
                  id="post-category-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 30, errorMessage: translate('entity.validation.maxlength', { max: 30 }) },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isEnabledLabel">
                  <AvInput id="post-category-isEnabled" type="checkbox" className="form-check-input" name="isEnabled" />
                  <Translate contentKey="listitApp.postCategory.isEnabled">Is Enabled</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="maxImagesLabel" for="post-category-maxImages">
                  <Translate contentKey="listitApp.postCategory.maxImages">Max Images</Translate>
                </Label>
                <AvField
                  id="post-category-maxImages"
                  type="string"
                  className="form-control"
                  name="maxImages"
                  validate={{
                    max: { value: 20, errorMessage: translate('entity.validation.max', { max: 20 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="validDaysLabel" for="post-category-validDays">
                  <Translate contentKey="listitApp.postCategory.validDays">Valid Days</Translate>
                </Label>
                <AvField
                  id="post-category-validDays"
                  type="string"
                  className="form-control"
                  name="validDays"
                  validate={{
                    max: { value: 90, errorMessage: translate('entity.validation.max', { max: 90 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="post-category-category">
                  <Translate contentKey="listitApp.postCategory.category">Category</Translate>
                </Label>
                <AvInput id="post-category-category" type="select" className="form-control" name="categoryId">
                  <option value="" key="0" />
                  {postCategories
                    ? postCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/post-category" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  postCategories: storeState.postCategory.entities,
  postCategoryEntity: storeState.postCategory.entity,
  loading: storeState.postCategory.loading,
  updating: storeState.postCategory.updating,
  updateSuccess: storeState.postCategory.updateSuccess,
});

const mapDispatchToProps = {
  getPostCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostCategoryUpdate);
