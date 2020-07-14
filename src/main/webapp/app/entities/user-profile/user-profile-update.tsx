import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserProfileUpdate = (props: IUserProfileUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [locationId, setLocationId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userProfileEntity, users, locations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-profile');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getLocations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.lastLoginDate = convertDateTimeToServer(values.lastLoginDate);
    values.lockDate = convertDateTimeToServer(values.lockDate);

    if (errors.length === 0) {
      const entity = {
        ...userProfileEntity,
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
          <h2 id="listitApp.userProfile.home.createOrEditLabel">
            <Translate contentKey="listitApp.userProfile.home.createOrEditLabel">Create or edit a UserProfile</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userProfileEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-profile-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="user-profile-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="phoneLabel" for="user-profile-phone">
                  <Translate contentKey="listitApp.userProfile.phone">Phone</Translate>
                </Label>
                <AvField
                  id="user-profile-phone"
                  type="text"
                  name="phone"
                  validate={{
                    maxLength: { value: 15, errorMessage: translate('entity.validation.maxlength', { max: 15 }) },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="emailEnabledLabel">
                  <AvInput id="user-profile-emailEnabled" type="checkbox" className="form-check-input" name="emailEnabled" />
                  <Translate contentKey="listitApp.userProfile.emailEnabled">Email Enabled</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="smsEnabledLabel">
                  <AvInput id="user-profile-smsEnabled" type="checkbox" className="form-check-input" name="smsEnabled" />
                  <Translate contentKey="listitApp.userProfile.smsEnabled">Sms Enabled</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="lastLoginDateLabel" for="user-profile-lastLoginDate">
                  <Translate contentKey="listitApp.userProfile.lastLoginDate">Last Login Date</Translate>
                </Label>
                <AvInput
                  id="user-profile-lastLoginDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastLoginDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userProfileEntity.lastLoginDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastLoginIpLabel" for="user-profile-lastLoginIp">
                  <Translate contentKey="listitApp.userProfile.lastLoginIp">Last Login Ip</Translate>
                </Label>
                <AvField id="user-profile-lastLoginIp" type="string" className="form-control" name="lastLoginIp" />
              </AvGroup>
              <AvGroup check>
                <Label id="isLockedLabel">
                  <AvInput id="user-profile-isLocked" type="checkbox" className="form-check-input" name="isLocked" />
                  <Translate contentKey="listitApp.userProfile.isLocked">Is Locked</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="lockDateLabel" for="user-profile-lockDate">
                  <Translate contentKey="listitApp.userProfile.lockDate">Lock Date</Translate>
                </Label>
                <AvInput
                  id="user-profile-lockDate"
                  type="datetime-local"
                  className="form-control"
                  name="lockDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userProfileEntity.lockDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="maxImagesLabel" for="user-profile-maxImages">
                  <Translate contentKey="listitApp.userProfile.maxImages">Max Images</Translate>
                </Label>
                <AvField
                  id="user-profile-maxImages"
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
                <Label id="validDaysLabel" for="user-profile-validDays">
                  <Translate contentKey="listitApp.userProfile.validDays">Valid Days</Translate>
                </Label>
                <AvField
                  id="user-profile-validDays"
                  type="string"
                  className="form-control"
                  name="validDays"
                  validate={{
                    max: { value: 90, errorMessage: translate('entity.validation.max', { max: 90 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isPrivacyEnabledLabel">
                  <AvInput id="user-profile-isPrivacyEnabled" type="checkbox" className="form-check-input" name="isPrivacyEnabled" />
                  <Translate contentKey="listitApp.userProfile.isPrivacyEnabled">Is Privacy Enabled</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="user-profile-user">
                  <Translate contentKey="listitApp.userProfile.user">User</Translate>
                </Label>
                <AvInput id="user-profile-user" type="select" className="form-control" name="userId">
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
                <Label for="user-profile-location">
                  <Translate contentKey="listitApp.userProfile.location">Location</Translate>
                </Label>
                <AvInput id="user-profile-location" type="select" className="form-control" name="locationId">
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
              <Button tag={Link} id="cancel-save" to="/user-profile" replace color="info">
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
  users: storeState.userManagement.users,
  locations: storeState.location.entities,
  userProfileEntity: storeState.userProfile.entity,
  loading: storeState.userProfile.loading,
  updating: storeState.userProfile.updating,
  updateSuccess: storeState.userProfile.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getLocations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserProfileUpdate);
