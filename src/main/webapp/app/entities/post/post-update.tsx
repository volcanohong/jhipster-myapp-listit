import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IPostCategory } from 'app/shared/model/post-category.model';
import { getEntities as getPostCategories } from 'app/entities/post-category/post-category.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IImage } from 'app/shared/model/image.model';
import { getEntities as getImages } from 'app/entities/image/image.reducer';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPostUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PostUpdate = (props: IPostUpdateProps) => {
  const [locationId, setLocationId] = useState('0');
  const [categoryId, setCategoryId] = useState('0');
  const [userId, setUserId] = useState('0');
  const [imageId, setImageId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { postEntity, locations, postCategories, users, images, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/post' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLocations();
    props.getPostCategories();
    props.getUsers();
    props.getImages();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);
    values.lastReviewedData = convertDateTimeToServer(values.lastReviewedData);

    if (errors.length === 0) {
      const entity = {
        ...postEntity,
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
          <h2 id="listitApp.post.home.createOrEditLabel">
            <Translate contentKey="listitApp.post.home.createOrEditLabel">Create or edit a Post</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : postEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="post-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="post-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="post-name">
                  <Translate contentKey="listitApp.post.name">Name</Translate>
                </Label>
                <AvField
                  id="post-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 4, errorMessage: translate('entity.validation.minlength', { min: 4 }) },
                    maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="detailLabel" for="post-detail">
                  <Translate contentKey="listitApp.post.detail">Detail</Translate>
                </Label>
                <AvField
                  id="post-detail"
                  type="text"
                  name="detail"
                  validate={{
                    maxLength: { value: 1024, errorMessage: translate('entity.validation.maxlength', { max: 1024 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="searchTextLabel" for="post-searchText">
                  <Translate contentKey="listitApp.post.searchText">Search Text</Translate>
                </Label>
                <AvField
                  id="post-searchText"
                  type="text"
                  name="searchText"
                  validate={{
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="post-price">
                  <Translate contentKey="listitApp.post.price">Price</Translate>
                </Label>
                <AvField id="post-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup check>
                <Label id="priceNegotiableLabel">
                  <AvInput id="post-priceNegotiable" type="checkbox" className="form-check-input" name="priceNegotiable" />
                  <Translate contentKey="listitApp.post.priceNegotiable">Price Negotiable</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="conditionLabel" for="post-condition">
                  <Translate contentKey="listitApp.post.condition">Condition</Translate>
                </Label>
                <AvInput
                  id="post-condition"
                  type="select"
                  className="form-control"
                  name="condition"
                  value={(!isNew && postEntity.condition) || 'NEW'}
                >
                  <option value="NEW">{translate('listitApp.ProductCondition.NEW')}</option>
                  <option value="EXCELLENT">{translate('listitApp.ProductCondition.EXCELLENT')}</option>
                  <option value="GOOD">{translate('listitApp.ProductCondition.GOOD')}</option>
                  <option value="FAIR">{translate('listitApp.ProductCondition.FAIR')}</option>
                  <option value="WORN">{translate('listitApp.ProductCondition.WORN')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="post-status">
                  <Translate contentKey="listitApp.post.status">Status</Translate>
                </Label>
                <AvInput
                  id="post-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && postEntity.status) || 'ACTIVE'}
                >
                  <option value="ACTIVE">{translate('listitApp.PostStatus.ACTIVE')}</option>
                  <option value="DELETED">{translate('listitApp.PostStatus.DELETED')}</option>
                  <option value="PENDING">{translate('listitApp.PostStatus.PENDING')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="post-createdDate">
                  <Translate contentKey="listitApp.post.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="post-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.postEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="post-lastModifiedDate">
                  <Translate contentKey="listitApp.post.lastModifiedDate">Last Modified Date</Translate>
                </Label>
                <AvInput
                  id="post-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.postEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastReviewedDataLabel" for="post-lastReviewedData">
                  <Translate contentKey="listitApp.post.lastReviewedData">Last Reviewed Data</Translate>
                </Label>
                <AvInput
                  id="post-lastReviewedData"
                  type="datetime-local"
                  className="form-control"
                  name="lastReviewedData"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.postEntity.lastReviewedData)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="reviewedCountLabel" for="post-reviewedCount">
                  <Translate contentKey="listitApp.post.reviewedCount">Reviewed Count</Translate>
                </Label>
                <AvField id="post-reviewedCount" type="string" className="form-control" name="reviewedCount" />
              </AvGroup>
              <AvGroup>
                <Label for="post-location">
                  <Translate contentKey="listitApp.post.location">Location</Translate>
                </Label>
                <AvInput id="post-location" type="select" className="form-control" name="locationId">
                  <option value="" key="0" />
                  {locations
                    ? locations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="post-category">
                  <Translate contentKey="listitApp.post.category">Category</Translate>
                </Label>
                <AvInput id="post-category" type="select" className="form-control" name="categoryId">
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
              <AvGroup>
                <Label for="post-user">
                  <Translate contentKey="listitApp.post.user">User</Translate>
                </Label>
                <AvInput id="post-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="post-image">
                  <Translate contentKey="listitApp.post.image">Image</Translate>
                </Label>
                <AvInput id="post-image" type="select" className="form-control" name="imageId">
                  <option value="" key="0" />
                  {images
                    ? images.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/post" replace color="info">
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
  locations: storeState.location.entities,
  postCategories: storeState.postCategory.entities,
  users: storeState.userManagement.users,
  images: storeState.image.entities,
  postEntity: storeState.post.entity,
  loading: storeState.post.loading,
  updating: storeState.post.updating,
  updateSuccess: storeState.post.updateSuccess,
});

const mapDispatchToProps = {
  getLocations,
  getPostCategories,
  getUsers,
  getImages,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostUpdate);
